package jldapsearch;

public class App {
	public static void main(String[] args) {
		System.out.println("");
		System.out.println("jldapsearch");
		System.out.println("---------------------------------------------------");
		try {
			Params.initialize(args);
			LdapSearch ldapSearch = new LdapSearch();
			ldapSearch.search();
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
