import { React, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

const CourseDetails = () => {
    const params = useParams();
    const [loading, setLoading] = useState(false);
    const [courseDetails, setCourseDatails] = useState(null);
    const initCourseDetails = {
        id: params.id,
        name: 'course.name',
        description: 'course.description'
    };

    useEffect(() => {
        setLoading(true);

        // fetch('/courses/{course.id}')
        //     .then((response) => response.json())
        //     .then((data) => {
        //         setCourses(data);
        //         setLoading(false);
        //     });
        console.log(params.id);
        setCourseDatails(initCourseDetails);
        console.log(courseDetails);
        setLoading(false);
    }, []);

    if (loading) {
        return <div>Loading...</div>;
    }

    return (
        <div className="course-details-page">
            <h2>{initCourseDetails.name}</h2>
            <div></div>
        </div>
    );
};

export default CourseDetails;
