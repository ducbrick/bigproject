package threeoone.bigproject.controller.requestbodies;

import threeoone.bigproject.controller.MemberQueryType;
import threeoone.bigproject.entities.Member;

/**
 * Represents a query related to a user.
 *
 * @param type the type of the user query, defined by {@link MemberQueryType}
 * @param member the user involved in the query, represented by {@link threeoone.bigproject.entities.Member}
 */
public record MemberQuery(MemberQueryType type, Member member) {
}
