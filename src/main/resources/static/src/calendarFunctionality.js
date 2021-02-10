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
    const  error = {
          code: 400,
          message: response.error.message
      }
      return error;
  }

  else {
    let busyTimeArray = response.calendars[calendarId].busy;
    return busyTimeArray;
  }
}

function generateFreeDate( startDate, endDate){
    const day = new Date(startDate).getDate();
    const month = new Date(startDate).getMonth();
    const year = new Date(startDate).getFullYear();
    const currentDay = new Date(year,month,day);
    
    console.log("CURRENT DAY :", currentDay);

}

async function getFreeTime(calendarId, startDate, endDate) {
//   let date = new Date(startDate);
   

  let res = await getBusyTime(calendarId, startDate, endDate);
//   generateFreeDate(startDate, endDate)
//     if(res.length == 0){
        
//     }


  console.log(res);
}

getFreeTime(
  "razvannechifor00@gmail.com",
  "2021-02-09T19:00:00.000Z",
  "2021-02-09T20:00:00.000Z"
);