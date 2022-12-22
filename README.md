## jldapsearch

> I needed a portable simple no install ldapsearch utility for use on any OS but specifically due to usage of Citrix that can perform queries quickly.  This is not intended to be a replacement for anything just a simple utlity for personal use.  I'm releasing it as I couldn't find anything similar online that worked against Active Directory and Enterprise Directory and thought it may be useful to others.

### Name ###
jldapsearch - Java LDAP Search Utility

### Synopsis ###
**ldapsearch** \[**-b** searchbase] \[**-s** {**base|one|sub|children**}] \[**-a** {**never|always|search|find**}] \[**-l** timelimit] \[**-z** sizelimit] \[**-D** binddn] \[**-w** passwd] \[**-H** ldapuri] *filter* [attrs...]


### Description ###
*jldapsearch* is a Java command-line LDAP search utility.

ldapsearch opens a connection to an LDAP server, binds, and performs a search using specified parameters. The filter should conform to the string representation for search filters as defined in RFC 4515. If not provided, the default filter, (objectClass=*), is used.

If ldapsearch finds one or more entries, the attributes specified by attrs are returned.

### Options ###

**-b** searchbase

Use searchbase as the starting point for the search instead of the default.

**-s** {base|one|sub|children}

Specify the scope of the search to be one of base, one, sub, or children to specify a base object, one-level, subtree, or children search. The default is sub. Note: children scope requires LDAPv3 subordinate feature extension.

**-a {never|always|search|find}**

Specify how aliases dereferencing is done. Should be one of never, always, search, or find to specify that aliases are never dereferenced, always dereferenced, dereferenced when searching, or dereferenced only when locating the base object for the search. The default is to never dereference aliases.

**-l** timelimit

wait at most timelimit seconds for a search to complete. A timelimit of 0 (zero) or none means no limit. A timelimit of max means the maximum integer allowable by the protocol. A server may impose a maximal timelimit which only the root user may override.

**-z** sizelimit

retrieve at most sizelimit entries for a search. A sizelimit of 0 (zero) or none means no limit. A sizelimit of max means the maximum integer allowable by the protocol. A server may impose a maximal sizelimit which only the root user may override.

**-D** binddn

Use the Distinguished Name binddn to bind to the LDAP directory. For SASL binds, the server is expected to ignore this value.

**-w** passwd

Use passwd as the password for simple authentication.

**-H** ldapuri

Specify URI(s) referring to the ldap server(s); a list of URI, separated by whitespace or commas is expected; only the protocol/host/port fields are allowed. As an exception, if no host/port is specified, but a DN is, the DN is used to look up the corresponding host(s) using the DNS SRV records, according to RFC 2782. The DN must be a non-empty sequence of AVAs whose attribute type is "dc" (domain component), and must be escaped according to RFC 2396.

### Output Format ###

If one or more entries are found, each entry is written to standard output in LDAP Data Interchange Format or ldif(5):

```
 version: 1

 # bjensen, example, net
 dn: uid=bjensen,dc=example,dc=net
 objectClass: person
 objectClass: dcObject
 uid: bjensen
 cn: Barbara Jensen
 sn: Jensen
 ...
```

### Examples ###

```
// NOTE:  In my Citrix Windows instance I had to change objectCategory to objectCategorx,
//        The code will replace this.  If anyone knows why, send help.
java -jar jldapsearch.jar -b OU=People,dc=example,dc=net -s sub -D "CN=test,OU=Admins,DC=example,DC=net" -w s3cr3t "(&(objectCategorx=person)(objectclass=organizationalPerson))"



```


### Ignore These Notes

https://stackoverflow.com/questions/39478482/how-to-create-development-branch-from-master-on-github
