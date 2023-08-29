package com.example.googlesheetskoreanvocabapp.common

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VerbInstance @Inject constructor() {
    private var verbGroupType: VerbGroupType? = VerbGroupType.ALL

    fun setVerbGroupType(verbGroupType: VerbGroupType) {
        this.verbGroupType = verbGroupType
    }

    fun returnGroupType(): VerbGroupType {
        return verbGroupType ?: VerbGroupType.ALL
    }
}

enum class VerbGroupType {
    OLD, NEW, ALL, COLORS, WEEKDAYS, UPDOWNLEFTRIGHT, ANIMAL, YUUN, BODYPARTS
}