package moe.gensoukyo.mcgproject.common.feature.customnpcs;

/**
 * NPC怪物信息
 */
public class NPCMob {

    public int tab;
    public String name;
    public double weight;
    public int spawnDelay = -1;

    /**
     * @param tab 服务端存储的NPC的tab
     * @param name NPC的名字
     * @param weight NPC在刷怪区中的权重
     */
    public NPCMob(int tab, String name, double weight) {
        this.tab = tab;
        this.name = name;
        this.weight = weight;
    }

    public NPCMob(int tab, String name, double weight, int spawnDelay) {
        this.tab = tab;
        this.name = name;
        this.weight = weight;
        this.spawnDelay = spawnDelay;
    }

}
