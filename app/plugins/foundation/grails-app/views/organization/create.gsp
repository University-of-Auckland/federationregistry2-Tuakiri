
<html>
	<head>
		
		<meta name="layout" content="members" />
		<title><g:message code="fedreg.view.members.organization.create.title" /></title>
	</head>
	<body>
		<section>
			<h2><g:message code="fedreg.view.members.organization.create.heading" /></h2>
			
			<g:render template="/templates/organization/create" plugin="federationcore" model="[saveAction:'save', requiresContactDetails:false]"/>
			
		</section>
	</body>
</html>
