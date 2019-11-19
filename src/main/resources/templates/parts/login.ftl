<#include "security.ftl">
<#macro login path isRegisterForm>
    <form action="${path}" method="post" xmlns="http://www.w3.org/1999/html">
        <div class="form-group row ml-5">
            <label class="col-sm-2 col-form-label" style="color: #c80201; font-weight: bold"> Пользователь: </label>
            <div class="col-sm-6">
                <input type="text" name="username"
                       class="form-control ${(usernameError??)?string('is-invalid', '')}" placeholder="Имя пользователя"
                       value="<#if user??>${user.username}</#if>"/>
                <#if usernameError??>
                    <div class="invalid-feedback">
                        ${usernameError}
                    </div>
                </#if>
            </div>
        </div>
        <div class="form-group row ml-5">
            <label class="col-sm-2 col-form-label" style="color: #c80201; font-weight: bold"> Пароль: </label>
            <div class="col-sm-6">
                <input type="password" name="password"
                       class="form-control ${(passwordError??)?string('is-invalid', '')}" placeholder="Пароль"
                       value=""/>
                <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </#if>
            </div>
        </div>
        <#if isRegisterForm>
            <div class="form-group row ml-5">
                <label class="col-sm-2 col-form-label" style="color: #c80201; font-weight: bold"> Повторите
                    пароль: </label>
                <div class="col-sm-6">
                    <input type="password" name="passwordConfirm"
                           class="form-control ${(passwordConfirmError??)?string('is-invalid', '')}"
                           placeholder="Повторите пароль"
                           value=""/>
                    <#if passwordConfirmError??>
                        <div class="invalid-feedback">
                            ${passwordConfirmError}
                        </div>
                    </#if>
                </div>
            </div>
            <div class="form-group row ml-5">
                <label class="col-sm-2 col-form-label" style="color: #c80201; font-weight: bold"> Email: </label>
                <div class="col-sm-6">
                    <input type="email" name="email"
                           class="form-control ${(emailError??)?string('is-invalid', '')}" placeholder="some@some.com"
                           value=""/>
                    <#if emailError??>
                        <div class="invalid-feedback">
                            ${emailError}
                        </div>
                    </#if>
                </div>
            </div>
            <div class="col-sm-4 mb-2 ml-5">
                <div class="g-recaptcha" data-sitekey="6LcWWbcUAAAAACalmOS_jK4jFzZIk0eUXUQz1JqC"></div>
                <#if captchaError??>
                    <div class="alert alert-danger" role="alert">
                        ${captchaError}
                    </div>
                </#if>
            </div>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <#if !isRegisterForm>
            <a href="/registration" class="btn btn-primary stretched-link">Добавить пользователя</a>
        </#if>
        <button type="submit" class="btn btn-primary">
            <#if !isRegisterForm>
                Войти
            <#else>
                Сохранить
            </#if>
        </button>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary">
            <#if user??>Выйти<#else>Войти</#if>
        </button>
    </form>
</#macro>