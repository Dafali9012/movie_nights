export { getBusyTime};

async function getBusyTime(calendarId, startDate, endDate) {
    // console.log("getFreeTime called !!");
    // const userId = "razvannechifor00@gmail.com";

    const data = {    
        timeMin: `${startDate}`,
        timeMax: `${endDate}`,
        // timeMin: "2021-02-08T14:00:00.000Z",
        // timeMax: "2021-02-08T18:00:00.000Z",
        timeZone: "GMT+0100",
        groupExpansionMax: "10",
        calendarExpansionMax: "10",
        items: [
            {
                id: `${calendarId}`,
                // id: "razvannechifor00@gmail.com",
                busy: "Active",
            },
        ],
    }

    let rawResponse = await fetch("https://www.googleapis.com/calendar/v3/freeBusy?key=AIzaSyBivsgRmuUrZ17Qk0PsZrxg2e5o3v_XkhA", {
     method: "post",
     headers: { "Content-Type": "application/json" },
     body: JSON.stringify(data),
    });
    
    let response = await rawResponse.json();
    console.log("RESPONSE ", response);
    // return response.calendars[userId].busy
}
