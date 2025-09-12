import java.util.List;

class Boss extends Villain {
    private static int LIMIT = 99999999;

    Boss(int id, int hp, int power) {
        super(id, hp, power, LIMIT);
        setName("보스" + id);
    }

    void attackAll(List<Hero> heroes) {
        try {
            if (isDead()) {
                throw new RuntimeException(getName() + "은/는 죽었기 때문에 다른 캐릭터를 공격할 수 없습니다.");
            }

            int attackPower = getPower() / heroes.size();

            for (Hero hero : heroes) {
                hero.setHp(hero.getHp() - attackPower);
            }

            System.out.printf("###%s이/가 %d만큼 광역 공격했습니다.\n", getName(), attackPower);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}
