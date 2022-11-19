import { React, useEffect, useState } from 'react'

export default function CourseDetails({ course }) {
    const [loading, setLoading] = useState(false)
    const [courseDetails, setCourseDatails] = useState(null)
    const initCourseDetails = {
        "id": course.id,
        "name": course.name,
        "description": course.description
    }

    useEffect(() => {
        setLoading(true);


        // fetch('/courses/{course.id}')
        //     .then((response) => response.json())
        //     .then((data) => {
        //         setCourses(data);
        //         setLoading(false);
        //     });
        setCourseDatails(initCourseDetails)
        console.log(courseDetails)
        setLoading(false)
    }, []);

    if (loading) {
        return <div>Loading...</div>
    }


    return (
        <div>
            <h2>{initCourseDetails.name}</h2>
            <div>

            </div>
        </div>
    )
}