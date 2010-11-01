package fedreg.wayf

import fedreg.core.*
import org.codehaus.groovy.grails.commons.ApplicationHolder

/*
 Integrates with Switch based WAYF by providing configuration for selectable categories etc
 Author: Bradley Beddoes
*/
class WayfController {
	
	def grailsApplication

	def generateconfiguration = {
		def organizationTypes = [] as List
		def identityProviders = IDPSSODescriptor.findAllWhere(active:true, approved:true) as List
		identityProviders.sort { it.displayName }
		def ssoPostEndpoints = [:]
		
		def types = grailsApplication.config.fedreg.metadata.wayf.generateconfig.orgtypes
		types.each { name ->
			def ot = OrganizationType.findWhere(name:name, discoveryServiceCategory:true)
			if(ot)
				organizationTypes.add(ot)
		}

		// Figure out SSO Post profile for each IDP, favouring SAML 2
		identityProviders.each { idp ->
			def ssoEndpoint = false
			idp.singleSignOnServices.each { ep ->
				if (ep.binding.uri == 'urn:mace:shibboleth:1.0:profiles:AuthnRequest') {
					ssoPostEndpoints.put(idp.id,  ep.location.uri)
					ssoEndpoint = true
				}
			}
			if(!ssoEndpoint) {
				idp.singleSignOnServices.each { ep ->
					if (ep.binding.uri == 'urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST')
						ssoPostEndpoints.put(idp.id,  ep.location.uri)
				}
			}
		}
		
		render view: "generateconfiguration", model:[organizationTypes:organizationTypes, identityProviders: IDPSSODescriptor.list(), ssoPostEndpoints:ssoPostEndpoints], contentType:"text/plain", encoding:"UTF-8"
	}

}