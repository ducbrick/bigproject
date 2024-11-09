package threeoone.bigproject.controller.requestbodies;


import threeoone.bigproject.controller.DocActionType;
import threeoone.bigproject.controller.MemActionType;
import threeoone.bigproject.entities.Member;

/**
 * A request body for make an action on given member
 *
 * @param type type of action {@link MemActionType}
 * @param member document need to make action on
 */
public record ActionOnMem(MemActionType type, Member member) {
}
