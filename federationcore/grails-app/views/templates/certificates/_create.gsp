<script type="text/javascript">
	var newCertificateValid = false;
	$(function() {
		$("#newcertificate").hide();
		$("#validcertificate").hide();
		
		$("#addcertficatelink").click(function() {
			$("#addcertificate").hide();
			$("#newcertificate").show('slide');
		});
		
		$("#closenewcertificatelink").click(function() {
			$("#newcertificate").hide('slide');
			$("#addcertificate").show('slide');
		});
		
		$("#newcertificatedata").bind('paste', function() { setTimeout(function() {fedreg.keyDescriptor_verify(); if(newCertificateValid) $("#validcertificate").show('highlight'); else $("#validcertificate").hide(); }, 100); });
	});
</script>

	<div id="addcertificate">
		<fr:button id="addcertficatelink" label="${message(code:'fedreg.label.addcertificate')}" icon="plus"/>
	</div>
	
	<div id="newcertificate">
		<h3><g:message code="fedreg.template.certificates.certificatemanagement.addnew.heading"/></h3>
		<p>
			<g:message code="fedreg.template.certificates.certificatemanagement.addnew.requestformat" />
		</p>
		<div id="newcertificatedetails">
		</div>
		<section id="validcertificate">
			<fr:button id="addnewcertificatelink" onclick="fedreg.keyDescriptor_create(); \$('#validcertificate').hide();" label="${message(code:'fedreg.label.add')}" icon="plus"/>
		</section>
		<g:textArea name="newcertificatedata" rows="25" cols="80"/><br>
		<fr:button id="closenewcertificatelink" label="${message(code:'fedreg.label.close')}" icon="close"/>
	</div>