package aaf.fr.foundation

import org.codehaus.groovy.grails.web.servlet.mvc.SynchronizerTokensHolder

import grails.test.spock.*
import aaf.fr.workflow.*
import aaf.fr.identity.Subject
import com.icegreen.greenmail.util.*

class BootstrapControllerSpec extends IntegrationSpec {	
	def controller, idpService, spService, organizationService, user
	
	def cleanup() {
	}
	
	def setup () {
        idpService = new IdentityProviderService()
        spService = new ServiceProviderService()
        organizationService = new OrganizationService()
		controller = new BootstrapController(IdentityProviderService:idpService, ServiceProviderService:spService, organizationService: organizationService)
		
        def user = Subject.build()
		SpecHelpers.setupShiroEnv(user)
	}
	
	def "Validate IDP start register"() {
		setup:
		(1..10).each {
			Organization.build(active:true, approved:true)
		}
		
		(1..11).each { i ->
			AttributeBase.build()
		}
		
		(1..12).each { i ->
			SamlURI.build(type:SamlURIType.NameIdentifierFormat)
		}
		
		when:
		def model = controller.idp()
		
		then:
		model.identityProvider != null
		model.identityProvider instanceof IDPSSODescriptor
	}
	
	def "Validate successful IDP registration"() {
		setup:
		def organization = Organization.build()
		def entityDescriptor = EntityDescriptor.build(organization:organization)
		def identityProvider = IDPSSODescriptor.build(entityDescriptor:entityDescriptor)
		def attributeAuthority = AttributeAuthorityDescriptor.build(entityDescriptor:entityDescriptor)
		def httpPost = SingleSignOnService.build(descriptor:identityProvider)
		def httpRedirect = SingleSignOnService.build(descriptor:identityProvider)
		def soapArtifact = SingleSignOnService.build(descriptor:identityProvider)
		def contact = aaf.fr.foundation.Contact.build()
		
		def ret = [:]
		ret.organization = organization
		ret.entityDescriptor = entityDescriptor
		ret.identityProvider = identityProvider
		ret.attributeAuthority = attributeAuthority
		ret.hostname = "test.host.com"
		ret.scope = "host.com"
		ret.httpPost = httpPost
		ret.httpRedirect = httpRedirect
		ret.soapArtifact = soapArtifact
		ret.contact = contact

    def token = SynchronizerTokensHolder.store(controller.session)
    controller.params[SynchronizerTokensHolder.TOKEN_URI] = "/bootstrap/saveidp"
    controller.params[SynchronizerTokensHolder.TOKEN_KEY] = token.generateToken(controller.params[SynchronizerTokensHolder.TOKEN_URI])
		
		when:
		idpService.metaClass.create = { def p -> 
			return [true, ret]
		} 
		controller.request.method = 'POST'
		def model = controller.saveidp()
		
		then:
		controller.response.redirectedUrl == "/registration/idpregistered/${identityProvider.id}"
	}
	
	def "Validate failed IDP registration"() {
		setup:
		def organization = Organization.build()
		def entityDescriptor = EntityDescriptor.build(organization:organization)
		def identityProvider = IDPSSODescriptor.build(entityDescriptor:entityDescriptor)
		def attributeAuthority = AttributeAuthorityDescriptor.build(entityDescriptor:entityDescriptor)
		def httpPost = SingleSignOnService.build(descriptor:identityProvider)
		def httpRedirect = SingleSignOnService.build(descriptor:identityProvider)
		def soapArtifact = SingleSignOnService.build(descriptor:identityProvider)
		def contact = aaf.fr.foundation.Contact.build()
		
		def ret = [:]
		ret.organization = organization
		ret.entityDescriptor = entityDescriptor
		ret.identityProvider = identityProvider
		ret.attributeAuthority = attributeAuthority
		ret.hostname = "test.host.com"
		ret.scope = "host.com"
		ret.httpPost = httpPost
		ret.httpRedirect = httpRedirect
		ret.soapArtifact = soapArtifact
		ret.contact = contact

    def token = SynchronizerTokensHolder.store(controller.session)
    controller.params[SynchronizerTokensHolder.TOKEN_URI] = "/bootstrap/saveidp"
    controller.params[SynchronizerTokensHolder.TOKEN_KEY] = token.generateToken(controller.params[SynchronizerTokensHolder.TOKEN_URI])
		
		when:
		this.idpService.metaClass.create = { def p -> 
			return [false, ret]
		} 
		controller.request.method = 'POST'
		def model = controller.saveidp()
		
		then:
		controller.flash.type == "error"
		controller.flash.message == "aaf.fr.foundation.idpssoroledescriptor.register.validation.error"
	}
	
	def "Validate IDP Registered with no supplied ID"() {
		when:
		def model = controller.idpregistered()
		
		then:
		controller.flash.type == "error"
		controller.flash.message == "controllers.fr.generic.namevalue.missing"
		controller.response.redirectedUrl == "/"
	}
	
	def "Validate IDP Registered with invalid supplied ID"() {
		setup:
		controller.params.id = -1
		
		when:
		def model = controller.idpregistered()
		
		then:
		controller.flash.type == "error"
		controller.flash.message == "aaf.fr.foundation.idpssoroledescriptor.nonexistant"
		controller.response.redirectedUrl == "/"
	}
	
	def "Validate SP register"() {
		setup:
		(1..10).each {
			Organization.build(active:true, approved:true)
		}
		
		(1..11).each { i ->
			AttributeBase.build()
		}
		
		(1..12).each { i ->
			SamlURI.build(type:SamlURIType.NameIdentifierFormat)
		}
		
		when:
		def model = controller.sp()
		
		then:
		model.serviceProvider != null
		model.serviceProvider instanceof SPSSODescriptor
	}
	
	def "Validate successful SP registration"() {
		setup:
		def organization = Organization.build(active:true, approved:true)
		def entityDescriptor = EntityDescriptor.build(organization:organization)
		def serviceProvider = SPSSODescriptor.build(entityDescriptor:entityDescriptor)
		def contact = Contact.build()
		
		def ret = [:]
		ret.organization = organization
		ret.entityDescriptor = entityDescriptor
		ret.serviceProvider = serviceProvider

    def token = SynchronizerTokensHolder.store(controller.session)
    controller.params[SynchronizerTokensHolder.TOKEN_URI] = "/bootstrap/savesp"
    controller.params[SynchronizerTokensHolder.TOKEN_KEY] = token.generateToken(controller.params[SynchronizerTokensHolder.TOKEN_URI])
		
		when:
		spService.metaClass.create = { def p -> 
			return [true, ret]
		} 
		controller.request.method = 'POST'
		def model = controller.savesp()
		
		then:
		controller.response.redirectedUrl == "/registration/spregistered/${serviceProvider.id}"	
	}
	
	def "Validate failed SP registration"() {
		setup:
		def organization = Organization.build(active:true, approved:true)
		def entityDescriptor = EntityDescriptor.build(organization:organization)
		def serviceProvider = SPSSODescriptor.build(entityDescriptor:entityDescriptor)
		
		def ret = [:]
		ret.organization = organization
		ret.entityDescriptor = entityDescriptor
		ret.serviceProvider = serviceProvider
		spService.metaClass.create = { def p -> 
			return [false, ret]	//.. deliberately leaving out other return vals here
		}

    def token = SynchronizerTokensHolder.store(controller.session)
    controller.params[SynchronizerTokensHolder.TOKEN_URI] = "/bootstrap/savesp"
    controller.params[SynchronizerTokensHolder.TOKEN_KEY] = token.generateToken(controller.params[SynchronizerTokensHolder.TOKEN_URI])
		
		when:
		controller.request.method = 'POST'
		def model = controller.savesp()
		
		then:
		controller.flash.type == "error"
		controller.flash.message == "aaf.fr.foundation.spssoroledescriptor.register.validation.error"	
	}
	
	def "Validate SP Registered with no supplied ID"() {
		when:
		def model = controller.spregistered()
		
		then:
		controller.flash.type == "error"
		controller.flash.message == "controllers.fr.generic.namevalue.missing"
		controller.response.redirectedUrl == "/"
	}
	
	def "Validate SP Registered with invalid supplied ID"() {		
		setup:
		controller.params.id = -1
		
		when:
		def model = controller.spregistered()
		
		then:
		controller.flash.type == "error"
		controller.flash.message == "aaf.fr.foundation.spssoroledescriptor.nonexistant"
		controller.response.redirectedUrl == "/"
	}
	
	def "Validate Organization register"() {
		setup:
		(1..10).each {
			OrganizationType.build()
		}
		
		when:
		controller.request.method = 'POST'
		def model = controller.organization()
		
		then:
		model.organization != null
		model.organization instanceof Organization
	}
	
	def "Validate successful organization register"() {
		setup:
		def organization = Organization.build(active:true, approved:true)
		def contact = Contact.build()

    def token = SynchronizerTokensHolder.store(controller.session)
    controller.params[SynchronizerTokensHolder.TOKEN_URI] = "/bootstrap/saveorganization"
    controller.params[SynchronizerTokensHolder.TOKEN_KEY] = token.generateToken(controller.params[SynchronizerTokensHolder.TOKEN_URI])
		
		when:
		organizationService.metaClass.create = { def p -> 
			return [true, organization, contact]
		} 
		controller.request.method = 'POST'
		def model = controller.saveorganization()
		
		then:
		controller.response.redirectedUrl == "/registration/organizationregistered/${organization.id}"	
	}
	
	def "Validate failed organization register"() {
		setup:
		def organization = Organization.build(active:true, approved:true)
		
    def token = SynchronizerTokensHolder.store(controller.session)
    controller.params[SynchronizerTokensHolder.TOKEN_URI] = "/bootstrap/saveorganization"
    controller.params[SynchronizerTokensHolder.TOKEN_KEY] = token.generateToken(controller.params[SynchronizerTokensHolder.TOKEN_URI])

		when:
		organizationService.metaClass.create = { def p -> 
			return [false, organization]
		} 
		controller.request.method = 'POST'
		def model = controller.saveorganization()
		
		then:
		controller.flash.type == "error"
		controller.flash.message == "domains.fr.foundation.organization.register.validation.error"	
	}
	
	def "Validate Organization Registered with no supplied ID"() {		
		when:
		def model = controller.organizationregistered()
		
		then:
		controller.flash.type == "error"
		controller.flash.message == "controllers.fr.generic.namevalue.missing"
		controller.response.redirectedUrl == "/"
	}
	
	def "Validate Organization Registered with invalid supplied ID"() {		
		setup:
		controller.params.id = -1
		
		when:
		def model = controller.organizationregistered()
		
		then:
		controller.flash.type == "error"
		controller.flash.message == "domains.fr.foundation.organization.nonexistant"
		controller.response.redirectedUrl == "/"
	}

}
