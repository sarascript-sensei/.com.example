<#include "security.ftl">
<#import "pager.ftl" as p>

<@p.pager url page />

<div class="card-columns" id="buildings-list">
    <#list page.content as builings>
        <div class="card my-3" data-id="${buildings.id}">
            <#if buildings.filename??>
                <img src="/img/${buildings.filename}" class="card-img-top" />
            </#if>
            <div class="m-2">
                <span>${buildings.name}</span><br/>
                <span>${buildings.description}</span><br/>
                <span>${buildings.address}</span><br/>
                <i>#${buildings.stages}</i>
            </div>
            <div class="card-footer text-muted container">
                <div class="row">
                    <a class="col align-self-center" href="/user-buildingss/${buildings.author.id}">${buildings.authorName}</a>
                    <a class="col align-self-center" href="/buildingss/${buildings.id}/like">
                        <#if buildings.meLiked>
                            <i class="fas fa-heart"></i>
                        <#else>
                            <i class="far fa-heart"></i>
                        </#if>
                        ${buildings.likes}
                    </a>
                    <#if buildings.author.id == currentUserId>
                        <a class="col btn btn-primary" href="/user-buildings/${buildings.author.id}?buildings=${buildings.id}">
                            Edit
                        </a>
                    </#if>
                </div>
            </div>
        </div>
    <#else>
        No buildings
    </#list>
</div>

<@p.pager url page />