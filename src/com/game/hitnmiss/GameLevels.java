/**
 * GameLevels.java
 */

package com.game.hitnmiss;

/**
 * 
 * @author bilal
 * 
 */
public class GameLevels {

    public static enum LEVEL_NAMES {
	LEVEL_ONE, LEVEL_TWO, LEVEL_THREE, LEVEL_FOUR, LEVEL_FIVE;
    }

    /** Singleton object. */
    private static GameLevels GAME_LEVELS;

    /** Current level. */
    private String _currentLevel;

    /** Total game points. */
    private int _totalPoints;

    /** Flag to keep track of game end. */
    private boolean _endGame;

    /**
     * private constructor.
     */
    private GameLevels() {
	_totalPoints = 0;
	_currentLevel = LevelOne.LEVEL_NAME;
	_endGame = false;
    }

    /**
     * Singleton class.
     * 
     * @return
     */
    public static GameLevels getInstance() {
	if (GAME_LEVELS == null) {
	    GAME_LEVELS = new GameLevels();
	}
	return GAME_LEVELS;
    }

    /**
     * Set current level.
     * 
     * @param gameLevel
     */
    public void setCurrentLevel(String gameLevel) {
	this._currentLevel = gameLevel;
    }

    /**
     * Get current level.
     * 
     * @return
     */
    public String getCurrentLevel() {
	return _currentLevel;
    }

    /**
     * Set total game point.
     * 
     * @param gamePoints
     */
    public void setGamePoints(int gamePoints) {
	this._totalPoints = gamePoints;
    }

    /**
     * Get game points.
     * 
     * @return
     */
    public int getGamePoints() {
	return _totalPoints;
    }

    /**
     * Class Level 1.
     * 
     * @author bilal
     * 
     */
    class LevelOne {

	public LevelOne levelOne;

	private static final String LEVEL_NAME = "LEVEL_ONE";

	private static final int MAX_HITS = 10;
	private static final int MAX_DUCK_IN_LEVEL = 10;
	private static final int LEVEL_POINTS = 1;
	private int _totalPoints;
	private int _hits;

	private LevelOne() {
	    _totalPoints = 0;
	    _hits = 0;
	}

	public LevelOne getInstance() {
	    if (levelOne == null) {
		levelOne = new LevelOne();
	    }
	    return levelOne;
	}

	public String getLevelName() {
	    return LEVEL_NAME;
	}

	public int getMaxHits() {
	    return MAX_HITS;
	}

	public int getMaxDucksLevel() {
	    return MAX_DUCK_IN_LEVEL;
	}

	public void setLevelPoints() {
	    _totalPoints += LEVEL_POINTS;
	}

	public int getLevelPoints() {
	    return _totalPoints;
	}

	public void setHits() {
	    _hits += 1;
	}

	public int getHits() {
	    return _hits;
	}

	public void resetHits() {
	    _hits = 0;
	}
    }

    /**
     * 
     * @author bilal
     * 
     */
    class LevelTwo {
	public LevelTwo levelTwo;

	private static final String LEVEL_NAME = "LEVEL_TWO";

	private static final int MAX_HITS = 7;
	private static final int MAX_DUCK_IN_LEVEL = 10;
	private static final int LEVEL_POINTS = 3;
	private int _totalPoints;
	private int _hits;

	private LevelTwo() {
	    _totalPoints = 0;
	    _hits = 0;
	}

	public LevelTwo getInstance() {
	    if (levelTwo == null) {
		levelTwo = new LevelTwo();
	    }
	    return levelTwo;
	}

	public String getLevelName() {
	    return LEVEL_NAME;
	}

	public int getMaxHits() {
	    return MAX_HITS;
	}

	public int getMaxDucksLevel() {
	    return MAX_DUCK_IN_LEVEL;
	}

	public void setLevelPoints() {
	    _totalPoints += LEVEL_POINTS;
	}

	public int getLevelPoints() {
	    return _totalPoints;
	}

	public void setHits() {
	    _hits += 1;
	}

	public int getHits() {
	    return _hits;
	}

	public void resetHits() {
	    _hits = 0;
	}
    }

    /**
     * 
     * @author bilal
     * 
     */
    class LevelThree {
	public LevelThree levelThree;

	private static final String LEVEL_NAME = "LEVEL_THREE";

	private static final int MAX_HITS = 5;
	private static final int MAX_DUCK_IN_LEVEL = 10;
	private static final int LEVEL_POINTS = 7;
	private int _totalPoints;
	private int _hits;

	private LevelThree() {
	    _totalPoints = 0;
	    _hits = 0;
	}

	public LevelThree getInstance() {
	    if (levelThree == null) {
		levelThree = new LevelThree();
	    }
	    return levelThree;
	}

	public String getLevelName() {
	    return LEVEL_NAME;
	}

	public int getMaxHits() {
	    return MAX_HITS;
	}

	public int getMaxDucksLevel() {
	    return MAX_DUCK_IN_LEVEL;
	}

	public void setLevelPoints() {
	    _totalPoints += LEVEL_POINTS;
	}

	public int getLevelPoints() {
	    return _totalPoints;
	}

	public void setHits() {
	    _hits += 1;
	}

	public int getHits() {
	    return _hits;
	}

	public void resetHits() {
	    _hits = 0;
	}
    }

    /**
     * 
     * @author bilal
     * 
     */
    class LevelFour {
	public LevelFour levelFour;

	private static final String LEVEL_NAME = "LEVEL_FOUR";

	private static final int MAX_HITS = 3;
	private static final int MAX_DUCK_IN_LEVEL = 10;
	private static final int LEVEL_POINTS = 5;
	private int _totalPoints;
	private int _hits;

	private LevelFour() {
	    _totalPoints = 0;
	    _hits = 0;
	}

	public LevelFour getInstance() {
	    if (levelFour == null) {
		levelFour = new LevelFour();
	    }
	    return levelFour;
	}

	public String getLevelName() {
	    return LEVEL_NAME;
	}

	public int getMaxHits() {
	    return MAX_HITS;
	}

	public int getMaxDucksLevel() {
	    return MAX_DUCK_IN_LEVEL;
	}

	public void setLevelPoints() {
	    _totalPoints += LEVEL_POINTS;
	}

	public int getLevelPoints() {
	    return _totalPoints;
	}

	public void setHits() {
	    _hits += 1;
	}

	public int getHits() {
	    return _hits;
	}

	public void resetHits() {
	    _hits = 0;
	}
    }

    /**
     * 
     * @author bilal
     * 
     */
    class LevelFive {
	public LevelFive levelFive;

	private static final String LEVEL_NAME = "LEVEL_FIVE";

	private static final int MAX_HITS = 3;
	private static final int MAX_DUCK_IN_LEVEL = 10;
	private static final int LEVEL_POINTS = 5;
	private int _totalPoints;
	private int _hits;

	private LevelFive() {
	    _totalPoints = 0;
	    _hits = 0;
	}

	public LevelFive getInstance() {
	    if (levelFive == null) {
		levelFive = new LevelFive();
	    }
	    return levelFive;
	}

	public String getLevelName() {
	    return LEVEL_NAME;
	}

	public int getMaxHits() {
	    return MAX_HITS;
	}

	public int getMaxDucksLevel() {
	    return MAX_DUCK_IN_LEVEL;
	}

	public void setLevelPoints() {
	    _totalPoints += LEVEL_POINTS;
	}

	public int getLevelPoints() {
	    return _totalPoints;
	}

	public void setHits() {
	    _hits += 1;
	}

	public int getHits() {
	    return _hits;
	}

	public void resetHits() {
	    _hits = 0;
	}
    }
}
