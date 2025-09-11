import java.util.List;

class Boss extends Villain {
    private static int LIMIT = 99999999;

    Boss(int id, int hp, int power) {
        super(id, hp, power, LIMIT);
        setName("보스" + id);
    }

    void attackAll(List<Hero> heroes) {
        int attackPower = getPower() / heroes.size();

        for (Hero hero : heroes) {
            hero.setHp(hero.getHp() - attackPower);
        }
    }
}
