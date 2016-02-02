package eu.ezpzcraft.pvpkit;

public class Kit
{
    private String name = null;
    // TODO: Inventory

    public Kit(String name)
    {
        this.name = name;

        // TODO: DB...
    }

    public void removeKit()
    {
        // TODO: remove from DB
    }

    public void updateKit()
    {
        // TODO: DB (create if do not exist)
    }
}
