<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="proFootballClubApp.train.home.createOrEditLabel"
          data-cy="TrainCreateUpdateHeading"
          v-text="$t('proFootballClubApp.train.home.createOrEditLabel')"
        >
          Create or edit a Train
        </h2>
        <div>
          <div class="form-group" v-if="train.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="train.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.train.training')" for="train-training">Training</label>
            <select
              class="form-control"
              name="training"
              :class="{ valid: !$v.train.training.$invalid, invalid: $v.train.training.$invalid }"
              v-model="$v.train.training.$model"
              id="train-training"
              data-cy="training"
            >
              <option
                v-for="training in trainingValues"
                :key="training"
                v-bind:value="training"
                v-bind:label="$t('proFootballClubApp.Training.' + training)"
              >
                {{ training }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.train.finishes')" for="train-finishes">Finishes</label>
            <div class="d-flex">
              <input
                id="train-finishes"
                data-cy="finishes"
                type="datetime-local"
                class="form-control"
                name="finishes"
                :class="{ valid: !$v.train.finishes.$invalid, invalid: $v.train.finishes.$invalid }"
                :value="convertDateTimeFromServer($v.train.finishes.$model)"
                @change="updateInstantField('finishes', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.train.player')" for="train-player">Player</label>
            <select class="form-control" id="train-player" data-cy="player" name="player" v-model="train.player">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="train.player && playerOption.id === train.player.id ? train.player : playerOption"
                v-for="playerOption in players"
                :key="playerOption.id"
              >
                {{ playerOption.id }}
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
            :disabled="$v.train.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./train-update.component.ts"></script>
