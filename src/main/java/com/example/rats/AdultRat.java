package com.example.rats;

import java.util.List;
import java.util.Random;

/**
 * This class holds the contents of the AdultRat Object.
 *
 * @author Fjoraldo Gordoni
 * @version 1.0
 * @since 2021/11/24
 */

public class AdultRat extends Rat {

	private static final int MOVE_INTERVAL = 4;
	private static final int INITIAL_WAIT_TIME = 2;
	private static final String ICON_PATH_TEMPLATE = "/images/rats/%s%s.png";
	private static final int BIRTH_TIMER = 10;
	private static final int MAX_BABIES = 5;
	private static final int WAIT_TIME_DURING_SEX = 6;

	private static final Random random = new Random();

	private final boolean isMale;
	private boolean isPregnant = false;
	private int babies = 0;

	/**
	 *
	 * @return
	 */
	public int getBabies() {
		return babies;
	}

	/**
	 *
	 * @param babies
	 */
	public void setBabies(int babies) {
		this.babies = babies;
	}

	/**
	 * Time until new birth.
	 */
	private int birthTimer = BIRTH_TIMER;

	/**
	 *
	 * @param babyRat the Instance of baby rat to be converted to adult.
	 */
	public AdultRat(BabyRat babyRat) {
		super(MOVE_INTERVAL, INITIAL_WAIT_TIME, babyRat.isSterile());
		this.isMale = babyRat.isMale();
	}

	/**
	 *
	 * @param isMale
	 */
	public AdultRat(boolean isMale) {
		super(MOVE_INTERVAL, INITIAL_WAIT_TIME, false);
		this.isMale = isMale;
	}

	/**
	 *
	 * @param rat
	 * @param isMale
	 */
	public AdultRat(Rat rat, boolean isMale) {
		super(rat);
		this.isMale = isMale;
	}

	// GETTERS AND SETTERS

	/**
	 *
	 * @return
	 */
	public boolean isMale() {
		return isMale;
	}

	/**
	 *
	 * @return
	 */
	public boolean isPregnant() {
		return isPregnant;
	}

	@Override
	public String getIconPath() {
		String sexOfRat = (this.isMale) ? "Male" : "Female";
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

		return String.format(ICON_PATH_TEMPLATE, sexOfRat, direction);
	}

	/**
	 * increment the state of the rat and return any new rats it creates.
	 *
	 * @return the rat created while iterating this one.
	 */
	@Override
	public Rat iterate() {
		super.iterate();
		//TODO: pregnancy
		if (isPregnant) {
			if (birthTimer == 0) {
				if (babies > 0) {
					babies -= 1;
					return new BabyRat(random.nextBoolean());
				} else {
					this.isPregnant = false;
				}
			} else {
				birthTimer -= 1;
			}
		}
		return null;
	}

	/**
	 * perform any actions this rat can perform.
	 * on the other rats in the given collection of rats
	 *
	 * @param rats the list of rats that this rat will act upon
	 * @return the resulting list of rats after
	 * the effects have been applied.
	 */
	@Override
	public List<Rat> act(List<Rat> rats) {
		//TODO: rat sex
		if (!this.isMale && !this.isPregnant) {
			for (Rat rat : rats) {
				if (rat instanceof AdultRat && ((AdultRat) rat).isMale && !rat.isDead()) {
					rat.setWaitTime(WAIT_TIME_DURING_SEX);
					this.setWaitTime(WAIT_TIME_DURING_SEX);
					babies = new Random().nextInt(MAX_BABIES);
					birthTimer = BIRTH_TIMER;
					this.isPregnant = true;
				}
			}
		}
		return rats;
	}

	@Override
	public int getPointsValue() {
		return 10 * (babies + 1);
	}

	/**
	 *
	 * @param isPregnant
	 */
	public void setIsPregnant(boolean isPregnant) {
		this.isPregnant = isPregnant;
	}
}
