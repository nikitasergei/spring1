<#import "parts/pageTemplate.ftl" as pt>
<@pt.page>
    <div class="container card mt-5 pt-3 text-primary bg-light" style="width: 50%">
        <div class="row no-gutters">
            <div class="col-md-4 border rounded">
                <img src="<#if item.photoAddress??>/img/${item.photoAddress}</#if>" class="card-img" alt="...">
            </div>
            <div class="col-md-8">
                <div class="card-body">
                    <label class="text-danger font-italic">Model:</label>
                    <#if item.model??>
                        <h5 class="card-title">${item.model}</h5></#if>
                    <label class="text-danger font-italic">Description</label>
                    <#if item.notes??>
                        <p class="card-text">${item.notes}</p></#if>
                    <label class="text-danger font-italic">Price: </label>
                    <#if item.price??>
                        <p class="card-text ">$ ${item.price}</p></#if>
                    <label class="text-danger font-italic">Available:</label>
                    <#if item.quantity??>
                        <p class="card-text">${item.quantity}</p></#if>
                    <form action="/itemInfo/${item.id}/add" class="float-left m-1">
                        <button type="submit" class="btn btn-danger"><i class="fas fa-cart-plus"></i> Add to
                            wishList
                        </button>
                    </form>
                    <button type="button" class="btn btn-success btn-lg"><i class="fab fa-cc-visa"></i> Buy now</button>
                </div>
            </div>
        </div>
    </div>
</@pt.page>