<%@page import="aaf.fr.foundation.MonitorType" %>

<fr:hasPermission target="federation:management:descriptor:${descriptor.id}:monitor:create">
  <hr>

  <div id="addmonitor">
    <a class="show-add-monitor btn"><g:message code="label.addmonitor"/></a>
  </div>
  
  <div id="newmonitor" class="revealable">
    <h4><g:message code="templates.fr.monitor.add.heading"/></h4>
    <form id="newmonitordata" class="form-horizontal">
      <fieldset>
       <g:hiddenField name="interval" value="0" />

        <div class="control-group">
          <label class="control-label" for="type"><g:message code="label.monitortype"/></label>
          <div class="controls">
            <g:select name="type" from="${MonitorType.list()}" optionKey="id" optionValue="name" class="span2"/>
            <fr:tooltip code='help.fr.monitor.type' />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label" for="url"><g:message code="label.location"/></label>
          <div class="controls">
            <input name="url" type="text" class="required span4" />
            <fr:tooltip code='help.fr.monitor.location' />
          </div>
        </div>

        <div class="control-group">
          <label class="control-label" for="node"><g:message code="label.node"/></label>
          <div class="controls">
            <input name="node" type="text" class="span2" />
            <fr:tooltip code="help.fr.monitor.node" />
          </div>
        </div>

        <div class="form-actions">
          <a class="add-monitor btn btn-success"><g:message code="label.add"/></a>
          <a class="cancel-add-monitor btn"><g:message code="label.cancel"/></a>
        </div>

      </fieldset>
    </form>
  </div>
  
</fr:hasPermission>

<div id="delete-monitor-modal" class="modal hide fade">
  <div class="modal-header">
    <a class="close close-modal">&times;</a>
    <h3><g:message code="templates.fr.monitor.delete.confirm.title"/></h3>
  </div>
  <div class="modal-body">
    <p><g:message code="templates.fr.monitor.delete.confirm.descriptive"/></p>
  </div>
  <div class="modal-footer">
    <a class="btn close-modal"><g:message code="label.cancel" /></a>
    <a class="btn btn-danger delete-monitor"><g:message code="label.delete" /></a>
  </div>
</div>
