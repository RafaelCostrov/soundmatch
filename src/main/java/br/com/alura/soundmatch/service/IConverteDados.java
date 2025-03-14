package br.com.alura.soundmatch.service;

public interface IConverteDados {
    <T> T converterDados(String json, Class<T> classe);
}
