export { getBusyTime };

async function getBusyTime(calendarId, startDate, endDate) {
    let newTime = new Date(startDate) - 3600000; ;
    newTime = new Date(newTime - 3600000).toISOString();

    const data = {
      timeMin: `${newTime}`,
      timeMax: `${endDate}`,
      timeZone: "GMT+0100",
      groupExpansionMax: "10",
      calendarExpansionMax: "10",
      items: [
        {
          id: `${calendarId}`,
        },
      ],
    };

  let rawResponse = await fetch(
    "https://www.googleapis.com/calendar/v3/freeBusy?key=AIzaSyBivsgRmuUrZ17Qk0PsZrxg2e5o3v_XkhA",
    {
      method: "post",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data),
    }
  );

  let response = await rawResponse.json();

  if(Object.keys(response).includes("error")){
    console.log("hello")
    const  error = {
      code: 400,
      message: response.error.message
    }
    return error;
  }

  return response.calendars[calendarId].busy;
}
