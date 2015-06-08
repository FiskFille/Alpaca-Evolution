package fiskfille.alpaca.common.entity;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import com.google.common.collect.Lists;

import cpw.mods.fml.common.registry.EntityRegistry;
import fiskfille.alpaca.Alpaca;

/**
 * Class for registering entities, removing entities and getting entities.
 * 
 * @author iLexiconn & Gegy1000 & FiskFille
 */
public class EntityHelper
{
    static int startEntityId = 0;

    private static Field classToIDMappingField;
    private static Field stringToIDMappingField;

    private static List<Class<? extends Entity>> removedEntities = Lists.newArrayList();

    public static boolean hasEntityBeenRemoved(Class<? extends Entity> entity)
    {
        return removedEntities.contains(entity);
    }

    static
    {
        int i = 0;

        for (Field field : EntityList.class.getDeclaredFields())
        {
            if (field.getType() == Map.class)
            {
                if (i == 3)
                {
                    field.setAccessible(true);
                    classToIDMappingField = field;
                }
                else if (i == 4)
                {
                    field.setAccessible(true);
                    stringToIDMappingField = field;
                    break;
                }

                i++;
            }
        }
    }

    public static void registerEntity(String entityName, Class<? extends Entity> entityClass)
    {
        int id = getUniqueEntityId();
        EntityRegistry.registerGlobalEntityID(entityClass, entityName, id);
        EntityRegistry.registerModEntity(entityClass, entityName, id, Alpaca.instance, 64, 1, true);
    }

    public static void registerEntity(String entityName, Class<? extends Entity> entityClass, int primaryColor, int secondaryColor)
    {
        int id = getUniqueEntityId();
        EntityRegistry.registerGlobalEntityID(entityClass, entityName, id);
        EntityRegistry.registerModEntity(entityClass, entityName, id, Alpaca.instance, 64, 1, true);
        EntityList.IDtoClassMapping.put(id, entityClass);
        EntityList.entityEggs.put(id, new EntityList.EntityEggInfo(id, primaryColor, secondaryColor));
    }

    public static void removeLivingEntity(Class<? extends EntityLiving> clazz)
    {
        removeEntity(clazz);
        removeEntityEgg(clazz);

        for (BiomeGenBase biome : BiomeGenBase.getBiomeGenArray())
        {
            if (biome != null)
            {
                EntityRegistry.removeSpawn(clazz, EnumCreatureType.ambient, biome);
                EntityRegistry.removeSpawn(clazz, EnumCreatureType.creature, biome);
                EntityRegistry.removeSpawn(clazz, EnumCreatureType.monster, biome);
                EntityRegistry.removeSpawn(clazz, EnumCreatureType.waterCreature, biome);
            }
        }
    }

    public static void removeEntity(Class<? extends Entity> clazz)
    {
        removedEntities.add(clazz);

        EntityList.IDtoClassMapping.remove(clazz);

        Object name = EntityList.classToStringMapping.get(clazz);

        EntityList.stringToClassMapping.remove(name);
        EntityList.classToStringMapping.remove(clazz);

        try
        {
            Map classToIDMapping = (Map) classToIDMappingField.get(null);
            Map stringToIDMapping = (Map) stringToIDMappingField.get(null);
            classToIDMapping.remove(clazz);
            stringToIDMapping.remove(name);
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }

    public static void removeEntityEgg(Class<? extends EntityLiving> clazz)
    {
        Integer toRemove = null;

        for (Object key : EntityList.entityEggs.keySet())
        {
            Integer intKey = (Integer) key;

            Class<? extends Entity> entityClass = EntityList.getClassFromID(intKey);

            if (clazz.equals(entityClass))
            {
                toRemove = intKey;

                break;
            }
        }

        if (toRemove != null)
        {
            EntityList.entityEggs.remove(toRemove);
        }
    }

    private static int getUniqueEntityId()
    {
        do
            startEntityId++;
        while (EntityList.getStringFromID(startEntityId) != null);
        return startEntityId;
    }

    public static Entity getEntityFromClass(Class entityClass, World world)
    {
        Entity entity = null;

        try
        {
            entity = (Entity) entityClass.getConstructor(new Class[] { World.class }).newInstance(world);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return entity;
    }
}