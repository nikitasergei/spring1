<#import "parts/pageTemplate.ftl" as pt>
<@pt.page>
    <div class="container alert alert-<#if messageType??>${messageType}</#if> mt-5 text-primary" style="width: 50%"
         role="alert">
        <h4 class="alert-heading">Well done!</h4>
        <p><#if message??>${message}</#if> </p>
        <hr>
        <form action="/user/wishList" class="m-1">
            <button type="submit" class="btn btn-danger"><i class="fas fa-shopping-bag"></i> Return to Wish List
            </button>
        </form>
    </div>
</@pt.page>