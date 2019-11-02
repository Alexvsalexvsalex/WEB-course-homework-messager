<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#-- @ftlvariable name="talkViews" type="ru.itmo.wp.model.service.TalkService.TalkView[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="form-box">
        <div class="header">Send message</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="sendTalk"/>
                <div class="field">
                    <div class="name">
                        <label for="login">Target</label>
                    </div>
                    <div class="value">
                        <select name="targetUserId">
                            <#list users as user>
                                <option value="${user.id}">${user.login}</option>
                            </#list>
                        </select>
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="login">Text</label>
                    </div>
                    <div class="value">
                        <textarea name="text"></textarea>
                    </div>
                </div>
                <div class="button-field">
                    <input type="submit" value="Send">
                </div>
            </form>
        </div>
    </div>
    <p/>
    <div class="datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Source</th>
                <th>Target</th>
                <th>Text</th>
                <th>Time</th>
            </tr>
            </thead>
            <tbody>
            <#list talkViews as talkView>
                <tr>
                    <td>${talkView.talk.id}</td>
                    <td>${talkView.sourceUser.login}</td>
                    <td>${talkView.targetUser.login}</td>
                    <td>${talkView.talk.text}</td>
                    <td>${talkView.talk.creationTime}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>
