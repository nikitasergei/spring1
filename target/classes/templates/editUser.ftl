<#import "parts/pageTemplate.ftl" as pt>

<@pt.page>
    <#if message??>message
    <#else>
        <div class="container text-danger mt-5" style="width: 50%">
            <h1>User editor</h1>

            <div class="form-group mt-3">
                <form action="/user" method="post">
                    <div class="form-group">
                        <input type="text" class="form-control" style="width: 500px" name="username"
                               value="${user.username}">
                    </div>
                    <#list roles as role>
                        <div class="form-check">
                            <label class="form-check-label">
                                <input type="checkbox" class="form-check-input"
                                       name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}
                            </label>
                        </div>
                    </#list>
                    <input type="hidden" value="${user.id}" name="userId">
                    <input type="hidden" value="${_csrf.token}" name="_csrf">
                    <div class="form-group mt-2">
                        <input type="submit" class="btn btn-primary" value="Submit">
                    </div>
                </form>
            </div>
        </div>
    </#if>
</@pt.page>