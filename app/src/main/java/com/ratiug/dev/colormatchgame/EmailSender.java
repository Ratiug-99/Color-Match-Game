package com.ratiug.dev.colormatchgame;

import android.util.Log;
import android.widget.Toast;

import com.github.tntkhang.gmailsenderlibrary.GMailSender;
import com.github.tntkhang.gmailsenderlibrary.GmailListener;

public class EmailSender { //

//    public void sendEmail(){
//        private void sendEmail() {
//            GMailSender.withAccount("color.match.game.feedback@gmail.com", "**********")
//                    .withTitle("Your score!")
//                    .withBody(tvScore.getText() + ". Not bad - not bad")
//                    .withSender("Color")
//                    .toEmailAddress(user.getUserEmail()) // one or multiple addresses separated by a comma
//                    .withListenner(new GmailListener() {
//                        @Override
//                        public void sendSuccess() {
//                            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void sendFail(String err) {
//                            Log.d("DBG | FINISHGAME ", "sendFail: " +  "Fail: " + err);
//                        }
//                    })
//                    .send();
//        }
//    }
}
