package com.deforestation;

//Work around for Gson having issues with integers, fixed in a 2021 PR: https://github.com/google/gson/pull/1290
public class DeforestationSerializable
{
    public int x;
    public int y;
    public int plane;
    public int id;
}
