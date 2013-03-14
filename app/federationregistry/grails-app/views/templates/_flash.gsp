<g:if test="${flash.type == 'info'}">
  <div class="alert alert-info">
    <strong><g:message encodeAs="HTML" code="label.info"/></strong><a class="close" data-dismiss="alert" href="#">&times;</a><br>
    <span>${flash.message}</span>
  </div>
</g:if>
<g:if test="${flash.type == 'success'}">
  <div class="alert alert-success">
    <strong><g:message encodeAs="HTML" code="label.success"/></strong><a class="close" data-dismiss="alert" href="#">&times;</a><br>
    <span>${flash.message}</span>
  </div>
</g:if>
<g:if test="${flash.type == 'error'}">
  <div class="alert alert-error">
    <strong><g:message encodeAs="HTML" code="label.error"/></strong><a class="close" data-dismiss="alert" href="#">&times;</a><br>
    <span>${flash.message}</span>
  </div>
</g:if>
