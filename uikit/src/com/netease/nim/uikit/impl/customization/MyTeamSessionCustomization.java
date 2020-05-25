package com.netease.nim.uikit.impl.customization;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.netease.nim.uikit.R;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.api.model.session.SessionCustomization;
import com.netease.nim.uikit.business.team.activity.AdvancedTeamInfoActivity;
import com.netease.nim.uikit.business.team.activity.AdvancedTeamMemberActivity;
import com.netease.nim.uikit.business.team.model.TeamExtras;
import com.netease.nim.uikit.business.team.model.TeamRequestCode;
import com.netease.nim.uikit.tongban.EditContentDialog_L;
import com.netease.nimlib.sdk.team.model.Team;

import java.util.ArrayList;

/**
 * SessionCustomization 可以实现聊天界面定制项：
 * 1. 聊天背景 <br>
 * 2. 加号展开后的按钮和动作，如自定义消息 <br>
 * 3. ActionBar右侧按钮。
 * <p>
 * DefaultTeamSessionCustomization 提供默认的群聊界面定制，添加了ActionBar右侧按钮，用于跳转群信息界面
 * <p>
 * Created by hzchenkang on 2016/12/20.
 */

public class MyTeamSessionCustomization extends SessionCustomization {

    public MyTeamSessionCustomization(String sessionId) {

        // ActionBar右侧按钮，跳转至群信息界面
        OptionsButton infoButton = new OptionsButton() {

            @Override
            public void onClick(final Context context, View view, final String sessionId) {
                Team team = NimUIKit.getTeamProvider().getTeamById(sessionId);
                if (team != null && team.isMyTeam()) {
//                    NimUIKit.startTeamInfo(context, sessionId);
                    AdvancedTeamMemberActivity.startActivityForResult((Activity) context, team.getId(), AdvancedTeamInfoActivity.REQUEST_CODE_MEMBER_LIST);

                } else {
                    final EditContentDialog_L dialog_l = new EditContentDialog_L(context, "申请进群", new EditContentDialog_L.OnReturnListener() {
                        @Override
                        public boolean onReturn(String content) {
                            if (content.equals("")){
                                Toast.makeText(context,"请输入申请留言！",Toast.LENGTH_SHORT).show();
                                return false;
                            }else {
                                //发送申请进群的广播
                                Intent intent = new Intent();
                                intent.putExtra("content",content);
                                intent.putExtra("group_id",sessionId);
                                intent.setAction("com.yiwo.friendscometogether.broadcastreceiver.MyShenQingJinQunBroadcastReceiver");
                                context.sendBroadcast(intent);
                                return true;
                            }
                        }
                    });
                    dialog_l.show();
                }
            }
        };
        Team team0 = NimUIKit.getTeamProvider().getTeamById(sessionId);
        if (team0!=null && team0.isMyTeam()){
            infoButton.iconId = R.drawable.nim_ic_message_actionbar_team_w;
        }else {
            infoButton.iconId = R.drawable.group_add;
        }

        buttons = new ArrayList<>();
        buttons.add(infoButton);
    }

    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if (requestCode == TeamRequestCode.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                String reason = data.getStringExtra(TeamExtras.RESULT_EXTRA_REASON);
                boolean finish = reason != null && (reason.equals(TeamExtras
                        .RESULT_EXTRA_REASON_DISMISS) || reason.equals(TeamExtras.RESULT_EXTRA_REASON_QUIT));
                if (finish) {
                    activity.finish(); // 退出or解散群直接退出多人会话
                }
            }
        }
    }

}
