# Oreo User Guide

// Update the title above to match the actual product name

// Product screenshot goes here

// Product intro goes here

## Tagging tasks

Attach tags while creating any task by writing one or more tags at the end of
the command. A tag starts with `#` and may contain letters, numbers, hyphens,
and underscores.

```text
todo watch a movie #fun #weekend
deadline submit report /by 2026-09-30 #school
event team meeting /from 2026-09-15 /to 2026-09-16 #project
```

Tags are shown after the task description in confirmations, task lists, and
search results. They are saved with the task when you exit Oreo.

## Updating tags

Add or remove one or more tags from an existing task using its task number.

```text
tag 1 #important #week1
untag 1 #fun
```

Repeated additions and removal of tags that are not present leave the task
unchanged.

## Listing tags

Use `list tags` to see every tag and the tasks associated with it.

```text
Here are the tags and their tasks:
#fun
  1.[T][ ] watch a movie #fun #weekend
#weekend
  1.[T][ ] watch a movie #fun #weekend
```

When no tasks have tags, Oreo displays `There are no tags in your list.`.
