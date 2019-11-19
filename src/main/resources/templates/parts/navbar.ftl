<#include "security.ftl">
<#import "login.ftl" as l>


<nav class="navbar navbar-expand-lg navbar-light bg-gradient-light" style="border-bottom: 1px solid lightgray"
     xmlns="http://www.w3.org/1999/html">
    <div class="nav-item mr-auto ml-auto"><a class="nav-link" href="/items"><h2 class="font-italic text-warning">This is
                why
                we play </h2></a>
    </div>
    <#if isAdmin>
        <form action="#">
            <button type="submit" class="btn btn-danger mr-3 ml-3 "><i class="fas fa-clipboard-list"></i> Orders list
            </button>
        </form>
    </#if>
    <#if isAdmin>
        <form action="/addItem">
            <button type="submit" class="btn btn-warning mr-3 ml-3 "><i class="far fa-plus-square"></i> Add an advert
            </button>
        </form>
    </#if>
    <#if user??>
        <form class="form-inline" method="get" action="/items">
            <input class="form-control mr-sm-2" type="text" name="filter" placeholder="Item's model" aria-label="Search">
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit"><i class="fas fa-search"></i> Search
            </button>
        </form>
    </#if>
    <#if user??>
        <#if isAdmin>
            <form action="/user">
                <button type="submit" class="btn btn-warring mr-3 ml-3 "><i class="fas fa-users"></i> Show All Users
                </button>
            </form>
        <#else>
            <form action="/user/wishList">
                <button type="submit" class="btn btn-danger rounded ml-2"><i class="fas fa-shopping-cart"></i> WishList
                </button>
            </form>
        </#if>
    </#if>
    <form action="/user/profile">
        <button type="submit" class="btn btn-info mr-3 ml-3"><#if user??><i
                    class="fas fa-user-check"></i>  ${name}<#else><i class="far fa-user"></i>  Guest</#if>
        </button>
    </form>

    <@l.logout />

</nav>

