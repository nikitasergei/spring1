<#import "parts/pageTemplate.ftl" as pt>
<#import "parts/pager.ftl" as p>
<@pt.page>
    <#if message??>
        <div class="alert alert-${messageType}" role="alert">
            ${message}
        </div>
    <#else>
        <#import "parts/pager.ftl" as p>
        <@p.pager url page/>
        <div class="card-columns m-5">
            <#list page.content as item>
                <div class="card">
                    <img src="<#if item.photoAddress??>/img/${item.photoAddress}</#if>"
                         class="card-img-top" alt="item photo">
                    <div class="card-body">
                        <label>Model:</label>
                        <h5 class="card-title border-bottom"><#if item.model??>${item.model}</#if></h5>
                        <label>Condition:</label>
                        <p class="card-text border-bottom"><#if item.condition??>${item.condition}</#if></p>
                        <label>Description:</label>
                        <p class="card-text border-bottom"><#if item.notes??>${item.notes}</#if></p>
                        <div class="card-text float-none mt-2">
                            <p class="float-left"> Price: <#if item.price??>${item.price} $</#if></p>
                            <p class="float-right">Quantity: <#if item.quantity??>${item.quantity}</#if></p>
                        </div>
                        <div class="card border border-light">
                            <form action="/itemInfo/${item.id}/" class="float-left m-1">
                                <button type="submit" class="btn btn-outline-info">More Info...</button>
                            </form>
                            <form action="/itemInfo/${item.id}/add" class="float-left m-1">
                                <button type="submit" class="btn btn-danger"><i class="fas fa-cart-plus"></i> Add to
                                    wishList
                                </button>
                            </form>
                            <form action="/itemInfo/2/" class="float-left m-1">
                                <button type="submit" class="btn btn-outline-success"><i class="fab fa-cc-visa"></i> Buy
                                    Item
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            <#else>
                <p style="color: #c80201">
                    Items list is empty!
                </p>
            </#list>
        </div>
        <@p.pager url page/>
    </#if>
</@pt.page>