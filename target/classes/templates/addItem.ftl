<#import "parts/pageTemplate.ftl" as pt>
<@pt.page>
    <div class="container mt-5 text-primary" style="width: 50%">
        <h3> Add Item </h3>
        <#if savingReport??>${savingReport}</#if>
        <div class="form-group mb-3">
            <form method="post" enctype="multipart/form-data">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <input type="hidden" name="id" value="<#if oneItem?? && oneItem.id?? >${oneItem.id}</#if>">
                <div class="form-group">
                    <label>Загрузите фото</label>
                    <div class="card-columns">
                        <div class="card p-5">
                            <i class="far fa-images"></i>
                        </div>
                        <div class="col-sm-6 p-5">
                            <input type="file" name="file" style="border-radius: 5px"/>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label>Choose item category</label>
                    <select class="custom-select" size="1" name="type">
                        <#if typeSet??>
                            <#list typeSet as type>
                                <option>${type}</option>
                            </#list>
                        </#if>
                    </select>
                    <label>Model</label>
                    <input type="text" name="model" class="form-control mb-3"${(modelError??)?string('is-invalid', '')}"
                    value="<#if item?? && item.model?? >${item.model}</#if>"
                    placeholder="Enter item model">
                    <div class="invalid-feedback mb-3">
                        <#if modelError??>${modelError}</#if>
                    </div>
                    <p><input name="condition" type="radio" value="Used"> Used </p>
                    <p><input name="condition" type="radio" value="New"> New </p>
                    <label>Description</label>
                    <input type="text" name="notes" class="form-control"
                           value="<#if item?? && item.notes?? >${item.notes}</#if>"
                           placeholder="Enter item description">
                    <label>Price: </label>
                    <input type="text" name="price" class="form-control${(priceError??)?string('is-invalid', '')}"
                           value="<#if item?? && item.price?? >${item.price}</#if>"
                           placeholder="Enter item price">
                    <div class="invalid-feedback">
                        <#if priceError??>${priceError}</#if>
                    </div>
                    <label>Quantity: </label>
                    <input type="text" name="quantity" class="form-control${(quantityError??)?string('is-invalid', '')}"
                           value="<#if item?? && item.quantity?? >${item.quantity}</#if>"
                           placeholder="Enter item quantity">
                    <div class="invalid-feedback">
                        <#if quantityError??>${quantityError}</#if>
                    </div>
                    <div class="form-group mt-5">
                        <input type="submit" class="btn btn-primary" value="Add">
                    </div>
                </div>
            </form>
        </div>
    </div>
</@pt.page>