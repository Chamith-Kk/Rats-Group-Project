package com.example.rats;

import java.util.List;

/**
 * This class holds the contents of the BabyRat Object.
 *
 * @author Cham
 * @version 1.0
 * @since 2021/11/09
 */
public class BabyRat extends Rat {

	private static final int MOVE_INTERVAL = 2;
	private static final int INITIAL_WAIT_TIME = 2;
	private static final String ICON_PATH_TEMPLATE = "/images/rats/baby%s.png";
	/**
	 * Beginning age of BabyRat
	 */
	private static final int BORN_AGE = 0;

	private static final int ADULT_AGE = 5;

    /**
     * Future type of BabyRat.
     */
    private final boolean isMale;
    /**
     *
     */
    private boolean isSterile;
    /**
     * Current age of BabyRat.
     */
    private int age;

    /**
     *
     * @param isMale
     */
    public BabyRat(boolean isMale) {
        super(MOVE_INTERVAL, INITIAL_WAIT_TIME, false);
        setAge(BORN_AGE);
        this.isMale = isMale;
    }

    /**
     *
     * @param babyRat
     * @param isMale
     */
    public BabyRat(BabyRat babyRat, boolean isMale) {
        super(babyRat);
        this.age = babyRat.getAge();
        this.isMale = isMale;
    }

	/**
	 * Checks if BabyRat can become Rat
	 *
	 * @return boolean true if yes
	 */

	public int getAge() {
		return age;
	}

    // SETTER AND GETTERS

    /**
     *
     * @param age
     */
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String getIconPath() {
        String direction;
        switch (this.getHeadDirection()) {
            case NORTH:
                direction = "Up";
                break;
            case EAST:
                direction = "Right";
                break;
            case WEST:
                direction = "Left";
                break;
            case SOUTH:
                direction = "Down";
                break;
            default:
                throw new NullPointerException();
        }
        return String.format(ICON_PATH_TEMPLATE, direction);
    }

    /**
     * increment the state of the rat and return any new rats it creates.
     *
     * @return the rat created while iterating this one if one exists.
     */
    @Override
    public Rat iterate() {
        super.iterate();
        age += 1;
        return null;
    }

    @Override
    public List<Rat> act(List<Rat> rats) {
        if (age >= ADULT_AGE) {
            rats.remove(this);
            rats.add(new AdultRat(this));
        }
        return rats;
    }

    @Override
    public int getPointsValue() {
        return 10;
    }

    /**
     *
     * @return
     */
    public boolean isMale() {
        return isMale;
    }
}
