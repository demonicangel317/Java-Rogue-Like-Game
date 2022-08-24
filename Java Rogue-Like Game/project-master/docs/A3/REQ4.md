# Requirement 4

**Title**:
Additional debuff

**Description**:

*Burned effect*:
When Mario sets foot on fire grounds (Lava `L`) in the lava zone, his maximum hit points will be reduced by 50 to balance out the increasing maximum when consuming Super Mushroom or water from the Health Fountain.
Mario's current hit points will be unaffected if it is below the new maximum, otherwise, it will be reduced to the new maximum.

*Broken leg effect*:
If Mario fails to make a jump thrice in a row, he will be plagued with the broken leg effect which lasts for 5 turns. During this period, Mario will be unable to make any jumps, and will also cause him to deal 10% less damage to enemies.
Consuming a Super Mushroom, or a Power Star will instantly heal this effect.

**Explanation why it adheres to SOLID principles** (WHY):

This goes in line with the open-closed principle whereby the methods in the Action class and Actor class are not needed to be altered to work with the additional debuffs, as these could be implemented using a enumeration of statuses.
This also adheres to the dependency inversion principle, as what goes on when Mario attacks or jumps is decided by the AttackAction and JumpAction classes respectively, and nothing is altered on the user's end when executing these actions, except for the result.



| Requirements                                                                                                            | Features (HOW) / Your Approach / Answer                                                                                                                                       |
| ----------------------------------------------------------------------------------------------------------------------- |-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Must use at least two (2) classes from the engine package                                                               | The additional effects will be added to the Status enumeration, and alterations will be made in the AttackAction and JumpAction classes respectively for the related effects. |
| Must use/re-use at least one(1) existing feature (either from assignment 2 and/or fixed requirements from assignment 3) | Both the AttackAction and JumpAction classes extend the existing abstract Action class, but changes need only be made in the former two.                                      |
| Must use existing or create new abstractions (e.g., abstract or interface, apart from the engine code)                  |                                                                                                                                                                               |
| Must use existing or create new capabilities                                                                            |                                                                                                                                                                               |
