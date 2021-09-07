package com.example.asintro

class MemoryDataBase {
    companion object {
        val trainerBeanArray = arrayListOf<TrainerBean>()
        init {
            trainerBeanArray.add(TrainerBean("Wilman","bad",null))
            trainerBeanArray.add(TrainerBean("Manuel","good",null))
            trainerBeanArray.add(TrainerBean("Alberto","worst", null))
        }
    }

}