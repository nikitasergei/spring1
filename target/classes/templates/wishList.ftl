<#import "parts/pageTemplate.ftl" as pt>
<@pt.page>
    <#import "parts/pager.ftl" as p>
    <div class="container card mt-5 ml-5 bg-light float-left" style="width: 70%">
        <div class="container card mt-2 pt-3 pb-3 bg-light border rounded">
            <h3>WishList of <abbr class="text-danger">${username}</abbr></h3>
        </div>
        <@p.pager url page/>
        <#list page.content as item>
            <div class="container card mt-2 pt-3 pb-3 text-black bg-light border rounded">
                <input type="checkbox" aria-label="Checkbox for following text input">
                <div class="card-body">
                    <div class="col-md-4 border rounded float-left m-3">
                        <img src="<#if item.photoAddress??>/img/${item.photoAddress}</#if>"
                             class="card-img" alt="...">
                    </div>
                    <div class="container m-3">
                        <div><label>Model:</label> <#if item.model??>${item.model}</#if></div>
                        <div class="container mb-2"><label>Description:</label>
                            <#if item.notes??>${item.notes}</#if></div>
                        <div><label>Size:</label> <#if item.size??>${item.size}</#if></div>
                        <div><label>Quantity:</label> <#if item.quantity??>${item.quantity}</#if></div>
                        <div><label>Price:</label> <#if item.price??>${item.price} $</#if></div>
                        <form action="/user/wishList/addPayment/${item.id}" class="float-right m-1">
                            <button type="submit" class="btn btn-success"><i class="fas fa-dollar-sign"></i>
                                Add to payment
                            </button>
                        </form>
                        <form action="/itemInfo/${item.id}/delete" class="float-right m-1">
                            <button type="submit" class="btn btn-danger"><i class="fas fa-trash-alt"></i>
                                Remove from wish List
                            </button>
                        </form>

                    </div>
                </div>
            </div>
        <#else>
            <p style="color: #c80201">
                Your WishList is empty! Start buy right now!
            </p>
        </#list>
        <@p.pager url page/>
    </div>
    <div class="container card mt-5 mr-5 bg-light float-right" style="width: 25%">
        <div class="container border-bottom m-2 p-3">
            <h2>Order</h2>
            <label class="float-left mt-2">Price</label> <label class="float-right mt-2">00.00 $</label>
        </div>
        <div class="container m-2 p-auto">
            <button type="button" class="btn btn-success btn-lg float-right"><i class="fas fa-dollar-sign"></i> Pay
            </button>
        </div>
    </div>

</@pt.page>