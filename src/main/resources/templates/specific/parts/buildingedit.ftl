
<a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
    buildings editor
</a>
<div class="collapse <#if buildings??>show</#if>" id="collapseExample">
    <div class="form-group mt-3">
        <form method="post" enctype="multipart/form-data">
            <div class="form-group">
                <input type="text" class="form-control ${(nameError??)?string('is-invalid', '')}"
                       value="<#if buildings??>${buildings.name}</#if>" name="name" placeholder="Введите название" />
                <#if nameError??>
                    <div class="invalid-feedback">
                        ${nameError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <input type="text" class="form-control"
                       value="<#if buildings??>${buildings.address}</#if>" name="address" placeholder="Введите адрес" />
                <#if addressError??>
                    <div class="invalid-feedback">
                        ${addressError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <input type="text" class="form-control ${(descriptionError??)?string('is-invalid', '')}"
                       value="<#if buildings??>${buildings.description}</#if>" name="description" placeholder="Введите описание" />
                <#if descriptionError??>
                    <div class="invalid-feedback">
                        ${descriptionError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <input type="number" class="form-control"
                       value="<#if buildings??>${buildings.stages}</#if>" name="stages" placeholder="Введите количество этажей" />
                <#if stagesError??>
                    <div class="invalid-feedback">
                        ${stagesError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <div class="custom-file">
                    <input type="file" name="file" id="customFile" />
                    <label class="custom-file-label" for="customFile">Choose file</label>
                </div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <input type="hidden" name="id" value="<#if buildings??>${buildings.id}</#if>" />
            <div class="form-group">
                <button type="submit" class="btn btn-primary">Save buildings</button>
            </div>
        </form>
    </div>
</div>
