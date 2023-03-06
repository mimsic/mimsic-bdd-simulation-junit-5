Given a comparator that accepts 2 values low and high

When low greater than or equal to high
Then comparator returns -100

When low greater than or equal to zero
Then comparator returns 1

When high less than or equal to zero
Then comparator returns -1

When none of the conditions match
Then comparator returns 0