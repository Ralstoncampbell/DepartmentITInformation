package com.example.departmentitinformation.Models

data class ModelCourse(
    var courseKey: String? = null,
    var courseCode: String? = null,
    var courseName: String? = null,
    var courseCredits: String? = null,
    var coursePreRequisites: String? = null,
    var courseDescription: String? = null
)