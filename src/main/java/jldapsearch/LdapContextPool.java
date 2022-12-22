package jldapsearch;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

public class LdapContextPool {

	private static List<LdapContext> contextPool = new ArrayList<LdapContext>();
	private static final int poolSize = 5;
	private static final Hashtable<String, String> env = new Hashtable<String, String>();

	private void validate() throws ConfigurationException {
		Log.verbose("validate");
		Utils.emptyConfigurationException("ldapuri", Params.ldapuri);
		Utils.emptyConfigurationException("binddn", Params.binddn);
		Utils.emptyConfigurationException("passwd", Params.passwd);
	}

	public LdapContextPool() throws ConfigurationException {
		validate();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, Params.ldapuri);
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, Params.binddn.trim());
		env.put(Context.SECURITY_CREDENTIALS, Params.passwd.trim());
		// Enable connection pooling
		env.put("com.sun.jndi.ldap.connect.pool", "true");
		Log.verbose("", env.get(Context.PROVIDER_URL), env.get(Context.SECURITY_PRINCIPAL));
	}

	public synchronized LdapContext openInitialContext() throws NamingException {
		Log.verbose("openInitialContext");
		LdapContext dirContext = null;
		// If a connection exists in the pool, use it
		if (contextPool.size() > 0) {
			dirContext = contextPool.remove(0);
			try { // /Make sure the connection is still valid
				dirContext.list("");
			} catch (Exception ex) {
				dirContext = new InitialLdapContext(env, null);
			}
		} else { // pool is empty
			dirContext = new InitialLdapContext(env, null);
		}
		return dirContext;
	}

	public synchronized void closeInitialContext(LdapContext initialCtx) throws NamingException {
		Log.verbose("closeInitialContext");
		if (contextPool.size() > poolSize)
			initialCtx.close();
		else
			contextPool.add(initialCtx);
	}

}
