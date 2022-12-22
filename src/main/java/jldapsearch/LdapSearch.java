package jldapsearch;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.PagedResultsControl;
import javax.naming.ldap.PagedResultsResponseControl;

public class LdapSearch {

	public void search() throws Exception {
		Log.verbose("process");
		int pageSize = 1000;
		int sizeLimit = Params.sizelimit;
		byte[] cookie = null;
		int currentEntry = 0;
		LdapContextPool lcp = new LdapContextPool();
		LdapContext ctx = lcp.openInitialContext();
		ctx.setRequestControls(new Control[] { new PagedResultsControl(pageSize, Control.CRITICAL) });
		SearchControls searchCtls = new SearchControls();
		searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		searchCtls.setReturningAttributes(Params.attributes);
		String searchBase = Params.searchbase;
		String searchFilter = Params.filter;
		do {
			NamingEnumeration<SearchResult> results = ctx.search(searchBase, searchFilter, searchCtls);
			while (results.hasMore() && currentEntry < sizeLimit) {
				currentEntry++;
				SearchResult searchResult = (SearchResult) results.next();
				String dn = searchResult.getName();
				Attributes attributes = searchResult.getAttributes();
				displayAttributes(dn, attributes);
			}
			Control[] controls = ctx.getResponseControls();
			if (controls != null) {
				for (int i = 0; i < controls.length; i++) {
					if (controls[i] instanceof PagedResultsResponseControl) {
						PagedResultsResponseControl prrc = (PagedResultsResponseControl) controls[i];
						cookie = prrc.getCookie();
					}
				}
			} else {
				System.out.println("No controls were sent from the server");
			}
			ctx.setRequestControls(new Control[] { new PagedResultsControl(pageSize, cookie, Control.CRITICAL) });
		} while (cookie != null && currentEntry < sizeLimit);
		lcp.closeInitialContext(ctx);
	}

	private void displayAttributes(String dn, Attributes attributes) throws NamingException {
		display("dn", dn);
		NamingEnumeration<? extends Attribute> ne = attributes.getAll();
		while (ne.hasMore()) {
			Attribute attribute = ne.next();
			String displayValue = getDisplayValue(attribute.getAll());
			display(attribute.getID(), displayValue);
		}
	}

	private void display(String key, String value) {
		System.out.print(key);
		System.out.print(": ");
		System.out.println(value);
	}

	private String getDisplayValue(NamingEnumeration<?> all) throws NamingException {
		List<String> values = getAttributeValues(all);
		if (values == null || values.size() == 0)
			return "";
		else if (values.size() == 1)
			return values.get(0);
		else
			return String.join(", ", values);
	}

	private List<String> getAttributeValues(NamingEnumeration<?> ne) throws NamingException {
		List<String> values = new ArrayList<String>();
		while (ne.hasMore()) {
			values.add(ne.next().toString());
		}
		return values;
	}

}
