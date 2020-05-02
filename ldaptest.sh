#
# OpenLDAP test server for qbteam
#

#
# LDAP server
# 
docker run --name qbteam-ldap-server-test --hostname qbteam-ldap-server-test --env LDAP_ORGANISATION="qbteam" --env LDAP_DOMAIN="qbteam.it" -p 389:389 -p 636:636 --detach osixia/openldap:1.3.0

#
# LDAP Client (for configuration and settings management purposes)
#
docker run -p 6443:443 -p 6080:80 --name qbteam-ldap-client-test --hostname qbteam-ldap-client-test --link qbteam-ldap-server-test:ldap-host --env PHPLDAPADMIN_LDAP_HOSTS=ldap-host --detach osixia/phpldapadmin:0.7.2