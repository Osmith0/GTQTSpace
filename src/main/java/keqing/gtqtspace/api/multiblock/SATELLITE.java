package keqing.gtqtspace.api.multiblock;

public class SATELLITE {
    public int circuit=-1;
    public int solarTier=0;
    public String SeniorTier="null";
    public String GeneratorTier="null";
    public int duration=114514;
    public Boolean using=false;
    public int getSolarTier()
    {
        return solarTier;
    }
    public String getSeniorTier()
    {
        return SeniorTier;
    }
    public  String getGeneratorTier()
    {
        return  GeneratorTier;
    }
    public int getDuration()
    {
        return duration;
    }
    public  Boolean getUsing()
    {
        return using;
    }
}
