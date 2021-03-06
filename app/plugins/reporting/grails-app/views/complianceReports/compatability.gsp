<html>
  <head>
    <meta name="layout" content="reporting" />  
  </head>
  <body>

    <div class="row">
      <div class="span11">
        <div class="span11">
          <form id="detailed-attributecompatibility-report-parameters" class="form form-inline well centered">
            <g:message encodeAs="HTML" code="label.identityprovider"/>
            <g:select name="idpID" from="${idpList.sort{it.displayName.toLowerCase()}}" optionKey="id" optionValue="${{ it.displayName?.encodeAsHTML() }}" class="span3"/>
            &nbsp;&nbsp;
            <g:message encodeAs="HTML" code="label.serviceprovider"/>
            <g:select name="spID" from="${spList.sort{it.displayName.toLowerCase()}}" optionKey="id" optionValue="${{ it.displayName?.encodeAsHTML() }}" class="span3"/>

            <a class="request-detailed-attributecompatibility-report btn"><g:message encodeAs="HTML" code="label.generate"/></a>
          </form>
      </div>
      </div>
    </div>

    <div class="row">
      <div class="span11">
        <div class="spinner centered"><r:img dir="images" file="spinner.gif"/></div>

        <div id="attributecompatibility" class="span9 offset2  hidden">
          <div class="row">
            <div class="unsupportedattributes span6 alert alert-box alert-error hidden">
              <h4 class="alert-heading"><g:message encodeAs="HTML" code="label.supportedattributesunavailable"/></h4>
              <p><g:message encodeAs="HTML" code="views.fr.reporting.federation.compliance.compatability.unavailable"/></p>
            </div>

            <div class="automaticrelease span6 alert alert-box hidden">
              <h4 class="alert-heading"><g:message encodeAs="HTML" code="label.noautomaticrelease"/></h4>
              <p><g:message encodeAs="HTML" code="views.fr.reporting.federation.compliance.compatability.automatic"/></p>
            </div>

            <div class="releasesuccess span6 alert alert-box alert-success hidden">
              <h4 class="alert-heading"><g:message encodeAs="HTML" code="label.successfulrelease"/></h4>
              <p><g:message encodeAs="HTML" code="views.fr.reporting.federation.compliance.compatability.success"/></p>
            </div>
          </div>
          
          <div class="row">
            <h3><g:message encodeAs="HTML" code="label.requestedattributes"/></h3>
            <h4><g:message encodeAs="HTML" code="label.requiredattributes"/></h4>
            <div class="requiredattributes span6"></div>
          </div>
          <div class="row">
            <h4><g:message encodeAs="HTML" code="label.optionalattributes"/></h4>
            <div class="optionalattributes span6"></div>
          </div>
        </div>
      </div>
    </div>

    <r:script>
      var attributecompatibilityEndpoint = "${createLink(controller:'complianceReports', action:'reportattributecompatibility')}"

      $(document).ready(function() {
        $('.request-detailed-attributecompatibility-report').click();
      });
    </r:script>
  </body>
</html>
