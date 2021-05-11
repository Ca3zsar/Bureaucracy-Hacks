from apscheduler.schedulers.blocking import BlockingScheduler
import executor

sched = BlockingScheduler()

@sched.scheduled_job('cron', day_of_week='sat', hour=10)
def scheduled_job():
    executor.refresh_info()

sched.start()
