<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="proFootballClubApp.cup.home.createOrEditLabel"
          data-cy="CupCreateUpdateHeading"
          v-text="$t('proFootballClubApp.cup.home.createOrEditLabel')"
        >
          Create or edit a Cup
        </h2>
        <div>
          <div class="form-group" v-if="cup.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="cup.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.cup.type')" for="cup-type">Type</label>
            <select
              class="form-control"
              name="type"
              :class="{ valid: !$v.cup.type.$invalid, invalid: $v.cup.type.$invalid }"
              v-model="$v.cup.type.$model"
              id="cup-type"
              data-cy="type"
              required
            >
              <option
                v-for="cupType in cupTypeValues"
                :key="cupType"
                v-bind:value="cupType"
                v-bind:label="$t('proFootballClubApp.CupType.' + cupType)"
              >
                {{ cupType }}
              </option>
            </select>
            <div v-if="$v.cup.type.$anyDirty && $v.cup.type.$invalid">
              <small class="form-text text-danger" v-if="!$v.cup.type.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.cup.events')" for="cup-events">Events</label>
            <input
              type="text"
              class="form-control"
              name="events"
              id="cup-events"
              data-cy="events"
              :class="{ valid: !$v.cup.events.$invalid, invalid: $v.cup.events.$invalid }"
              v-model="$v.cup.events.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.cup.finished')" for="cup-finished">Finished</label>
            <input
              type="checkbox"
              class="form-check"
              name="finished"
              id="cup-finished"
              data-cy="finished"
              :class="{ valid: !$v.cup.finished.$invalid, invalid: $v.cup.finished.$invalid }"
              v-model="$v.cup.finished.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.cup.entrance')" for="cup-entrance">Entrance</label>
            <input
              type="number"
              class="form-control"
              name="entrance"
              id="cup-entrance"
              data-cy="entrance"
              :class="{ valid: !$v.cup.entrance.$invalid, invalid: $v.cup.entrance.$invalid }"
              v-model.number="$v.cup.entrance.$model"
              required
            />
            <div v-if="$v.cup.entrance.$anyDirty && $v.cup.entrance.$invalid">
              <small class="form-text text-danger" v-if="!$v.cup.entrance.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.cup.entrance.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.cup.start')" for="cup-start">Start</label>
            <div class="d-flex">
              <input
                id="cup-start"
                data-cy="start"
                type="datetime-local"
                class="form-control"
                name="start"
                :class="{ valid: !$v.cup.start.$invalid, invalid: $v.cup.start.$invalid }"
                :value="convertDateTimeFromServer($v.cup.start.$model)"
                @change="updateInstantField('start', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.cup.goalScorer')" for="cup-goalScorer">Goal Scorer</label>
            <select class="form-control" id="cup-goalScorer" data-cy="goalScorer" name="goalScorer" v-model="cup.goalScorer">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="cup.goalScorer && playerOption.id === cup.goalScorer.id ? cup.goalScorer : playerOption"
                v-for="playerOption in players"
                :key="playerOption.id"
              >
                {{ playerOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.cup.newsPaper')" for="cup-newsPaper">News Paper</label>
            <select class="form-control" id="cup-newsPaper" data-cy="newsPaper" name="newsPaper" v-model="cup.newsPaper">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="cup.newsPaper && newsPaperOption.id === cup.newsPaper.id ? cup.newsPaper : newsPaperOption"
                v-for="newsPaperOption in newsPapers"
                :key="newsPaperOption.id"
              >
                {{ newsPaperOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.cup.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./cup-update.component.ts"></script>
