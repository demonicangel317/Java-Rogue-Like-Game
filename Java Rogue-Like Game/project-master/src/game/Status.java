package game;

/**
 * Use this enum class to give `buff` or `debuff`.
 * It is also useful to give a `state` to abilities or actions that can be attached-detached.
 */
public enum Status {
    HOSTILE_TO_ENEMY, // use this status to be considered hostile towards enemy (e.g., to be attacked by enemy)
    TALL, // use this status to tell that current instance has "grown".
    ON_TOP, // use this to indicate that actor is on top of ground
    INVINCIBLE, // use this to indicate that actor has consumed a power star
    POWER_UP, // use this as a marker that power water has been consumed
    HAS_WRENCH, //uses to show that player has equipped wrench
    HOSTILE_TO_PLAYER, // use this to indicate that actor has consumed a power star
    CAN_JUMP, // use this to show that the actor can jump
    PIRANHA_PLANT, //use this to show that the actor is a piranha plant
    BROKEN_LEG, // use this to indicate that the actor has failed to make a jump thrice in a row
    FAILED_ONCE, // use this to indicate that the actor has failed to make a jump once
    FAILED_TWICE, // use this to indicate that the actor has failed to make a jump twice
    END_GAME_KEY, //use this to indicate the item is an end game key
    HIGH_GROUND, // use this to indicate that the ground is a high ground
}
