

// Environmental configuration
environments {
    test {		
		log4j = {
			debug	'fedreg.workflow',
					'grails.app.service',
					'grails.app.domain'
			
			appenders {
				console name:'stdout', layout:pattern(conversionPattern: '%d %-5p: %m%n')
			}
		}
	}
}