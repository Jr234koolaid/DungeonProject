package edu.utsa.cs3443.dungeon.model;

import android.content.Context;
import android.widget.Toast;

public class AttackToast extends Toast {

    public AttackToast(Context context, String attacker, int damageDealt, String victim) {
        super(context);
        if (damageDealt == -1){
            Toast.makeText(context, victim + " dodged " + attacker + "'s attack!", Toast.LENGTH_SHORT).show();
        } else if (damageDealt == 0) {
            Toast.makeText(context, attacker + "'s attack missed " + victim + "!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, attacker + " hit " + victim + " for " + damageDealt + " damage!", Toast.LENGTH_SHORT).show();
        }
    }
}
