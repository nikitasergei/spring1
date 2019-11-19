<#import "parts/pageTemplate.ftl" as pt>

<@pt.page>
    <div class="container mt-5 text-danger" style="width: 50%">
        <div class="col-lg-4 col-md-12 mb-4 mt-2">
            <div class="card testimonial-card">
                <div class="card-up teal lighten-2">
                </div>
                <div class="avatar mx-auto white"><#if filename??><img
                        src="/img/${filename}"
                        alt="avatar mx-auto white" class="rounded-circle img-fluid">
                    <#else><img
                            src="https://image.shutterstock.com/image-vector/avatar-man-icon-profile-placeholder-260nw-1229859850.jpg"
                            alt="avatar mx-auto white" class="rounded-circle img-fluid">
                    </#if>
                </div>
                <div class="card-body">
                    <h4 class="card-title mt-1 ml-auto mr-auto">${username}</h4>
                    <hr>
                </div>
            </div>
        </div>
        <form method="post" enctype="multipart/form-data">
            <div class="form-group row">
                <label class="col-sm-2 col-form-label">Password:</label>
                <div class="col-sm-6">
                    <input type="password" name="password" class="form-control" placeholder="Password"/>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label" style="color: red">Email:</label>
                <div class="col-sm-6">
                    <input type="email" name="email" class="form-control" placeholder="some@some.com"/>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label" style="color: red">Image:</label>
                <div class="col-sm-6">
                    <input type="file" name="file" style="border-radius: 5px"/>
                </div>
            </div>

            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button class="btn btn-primary" type="submit">Save</button>
        </form>
    </div>
</@pt.page>