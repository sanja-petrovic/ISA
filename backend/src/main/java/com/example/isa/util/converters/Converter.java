package com.example.isa.util.Converters;

public interface Converter<Entity, Dto> {

    public Dto entityToDto(Entity entity);
    public Entity dtoToEntity(Dto dto);
}
